module ukitinu.spring {
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.web;
    requires org.apache.tomcat.embed.core;
    requires spring.core;
    requires spring.webmvc;
    requires json.simple;
    requires org.slf4j;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires java.validation;
    requires ukitinu.commons;
    requires ukitinu.database;
}