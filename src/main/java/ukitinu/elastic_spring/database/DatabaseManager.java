package ukitinu.elastic_spring.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import ukitinu.elastic_spring.database.exceptions.DatabaseException;
import ukitinu.elastic_spring.database.search.Query;
import ukitinu.elastic_spring.database.search.Search;

import java.util.List;
import java.util.Map;

public enum DatabaseManager
{
    INSTANCE;

    private static final Logger LOG = LoggerFactory.getLogger(DatabaseManager.class);
    private static final ElasticClient databaseClient = ElasticClient.getInstance();

    public Document get(String index, String id) throws DatabaseException
    {
        Document document = databaseClient.getSingle(index, id);
        if (document == null) throw new DatabaseException(index + "/" + id + " not found", HttpStatus.NOT_FOUND);
        return document;
    }

    public Document pop(String index, String id) throws DatabaseException
    {
        Document document = databaseClient.getSingle(index, id);
        if (document == null) throw new DatabaseException(index + "/" + id + " not found", HttpStatus.NOT_FOUND);
        deleteSingle(index, id);
        return document;
    }

    public void indexSingle(String index, String id, Map<String, ?> document) throws DatabaseException
    {
        String response = databaseClient.indexSingle(index, id, document);
        LOG.info(response);
    }

    public void indexBulk(String index, String id, Map<String, ?> document)
    {
        databaseClient.indexBulk(index, id, document);
    }

    public boolean exists(String index, String id) throws DatabaseException
    {
        return databaseClient.exists(index, id);
    }

    public void deleteSingle(String index, String id) throws DatabaseException
    {
        String response = databaseClient.deleteSingle(index, id);
        LOG.info(response);
    }

    public void deleteBulk(String index, String id)
    {
        databaseClient.deleteBulk(index, id);
    }

    public void updateSingle(String index, String id, Map<String, ?> document) throws DatabaseException
    {
        String response = databaseClient.updateSingle(index, id, document);
        LOG.info(response);
    }

    public void updateBulk(String index, String id, Map<String, ?> document)
    {
        databaseClient.updateBulk(index, id, document);
    }

    public SearchResult search(Search search) throws DatabaseException
    {
        return databaseClient.search(search);
    }

    public long count(String index, Query query) throws DatabaseException
    {
        return databaseClient.count(index, query);
    }

    public boolean exists(String index, Query query) throws DatabaseException
    {
        return count(index, query) > 0;
    }

    public Document getFirstDocument(Search search) throws DatabaseException
    {
        SearchResult result = search(search);
        if (result.getDocuments().isEmpty()) throw new DatabaseException("Document not found", HttpStatus.NOT_FOUND);
        return result.getDocuments().get(0);
    }

    public BucketAggregationResult aggregateTerm(String index, Query query, String field, int size, List<String> excludeKw) throws DatabaseException
    {
        return databaseClient.aggregateTerm(index, query, field, size, excludeKw);
    }

    public BucketAggregationResult getTimeHistogram(String index, Query query, String field, HistogramInterval interval) throws DatabaseException
    {
        return databaseClient.getTimeHistogram(index, query, field, interval);
    }
}
