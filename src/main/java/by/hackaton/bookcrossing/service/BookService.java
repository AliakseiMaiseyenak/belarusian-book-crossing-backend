package by.hackaton.bookcrossing.service;

import by.hackaton.bookcrossing.dto.BookDto;
import by.hackaton.bookcrossing.dto.BookFilter;
import by.hackaton.bookcrossing.dto.BookShortDto;
import by.hackaton.bookcrossing.entity.Account;
import by.hackaton.bookcrossing.entity.Book;
import by.hackaton.bookcrossing.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class BookService {

    private String url = "http://classify.oclc.org/classify2/Classify?isbn=%s&summary=true";

    @PersistenceContext
    private EntityManager em;

    private BookRepository bookRepository;
    private ModelMapper modelMapper;

    public BookService(BookRepository bookRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    public List<BookDto> getBooks() {
        return bookRepository.findAll().stream().map(b -> modelMapper.map(b, BookDto.class)).collect(toList());
    }

    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow();
        return modelMapper.map(book, BookDto.class);
    }

    public List<BookDto> getMyBooks(String email) {
        return bookRepository.findByOwner_Username(email).stream().map(b -> modelMapper.map(b, BookDto.class)).collect(toList());
    }

    public void createBook(BookDto dto, Account owner) {
        Book book = modelMapper.map(dto, Book.class);
        book.setOwner(owner);
        bookRepository.save(book);
    }

    public void updateBook(Long id, BookDto dto) {
        Book book = bookRepository.findById(id).orElseThrow();
        modelMapper.map(book, dto);
        bookRepository.save(book);
    }

    public List<String> autoComplete(BookFilter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<String> criteria = cb.createQuery(String.class);
        Root<Book> root = criteria.distinct(true).from(Book.class);
        switch (filter.getField()) {
            case TITLE:
                criteria.select(root.get("title")).where(cb.like(root.get("title"), "%" + filter.getText().toLowerCase() + "%"));
                break;
            case AUTHOR:
                criteria.select(root.get("author")).where(cb.like(root.get("author"), "%" + filter.getText().toLowerCase() + "%"));
                break;
            default:
                break;
        }
        return em.createQuery(criteria).getResultList();
    }

    public BookShortDto findBookByISBN(String isbn) {
        Optional<Book> book = bookRepository.findByISBN(isbn);
        if (book.isPresent()) {
            return modelMapper.map(book, BookShortDto.class);
        }
        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(String.format(url, isbn))).build();
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(response.body().toString())));
            doc.getDocumentElement().normalize();


            XPath xpath = XPathFactory.newInstance().newXPath();
            Node respNode = getNode(xpath, doc);
            if (respNode != null) {
                String respCode = getAttribute(respNode, "@code");
                if ("4".equals(respCode)) {
                    String expression = "//works/work";
                    NodeList list = (NodeList) xpath.evaluate(expression, doc, XPathConstants.NODESET);
                    Node node = list.item(0);
                    String title = getAttribute(node, "@title");
                    String author = getAttribute(node, "@author");
                    BookShortDto dto = new BookShortDto();
                    dto.setAuthor(author);
                    dto.setTitle(title);
                    dto.setISBN(isbn);
                    return dto;

                    //String date = getAttribute(xpath, node, "@lyr");
                    //String editions = getAttribute(xpath, node, "@editions");
                    //String format = getAttribute(xpath, node, "@format");
                    //String owi = getAttribute(xpath, node, "@owi");
                    //System.out.println("title=" + title + " author=" + author + " editions=" + editions + " date=" + date + " format=" + format + " owi=" + owi);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Node getNode(XPath xpath, Node parent) {
        String expression = "//response";
        Node node = null;
        try {
            node = (Node) xpath.evaluate(expression, parent, XPathConstants.NODE);
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
        return node;
    }

    private String getAttribute(Node node, String expression) {
        String attrValue = null;
        try {
            NamedNodeMap attrs = node.getAttributes();
            for (int i = 0; i < attrs.getLength(); i++) {
                Node a = attrs.item(i);
                if (a.getNodeName().equals(expression.substring(1)))
                    attrValue = a.getNodeValue();
            }
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }

        return attrValue;
    }
}
