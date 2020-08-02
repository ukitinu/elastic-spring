module ukitinu.database {
    requires org.slf4j;
    requires spring.web;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires json.simple;
    requires org.apache.httpcomponents.httpcore;
    requires ukitinu.commons;
    requires elasticsearch.rest.high.level.client;
    requires elasticsearch;
    requires elasticsearch.rest.client;
    requires elasticsearch.core;

    exports ukitinu.database;
    exports ukitinu.database.exceptions;
    exports ukitinu.database.search;
}