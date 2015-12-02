package adzerk;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.Symbol;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class BootLambda implements RequestStreamHandler {

  public final String PROPERTY_FILE_NAME = "/adzerk.lambda.properties";
  public final String HANDLER_PROP_NAME  = "handler-var";

  public IFn require      = Clojure.var("clojure.core", "require");
  public IFn namespace    = Clojure.var("clojure.core", "namespace");
  public IFn name         = Clojure.var("clojure.core", "name");
  public Properties props = new Properties();

  public Symbol sym;
  public String symNs;
  public String symName;
  public IFn handler;

  public BootLambda() throws Exception {
    try (final InputStream stream = this.getClass().getResourceAsStream(PROPERTY_FILE_NAME)) {
      props.load(stream);
    } catch (Exception e) {
      System.err.println("Error loading "+PROPERTY_FILE_NAME);
      throw e;
    }
    sym       = (Symbol)Clojure.read(props.getProperty(HANDLER_PROP_NAME));
    symNs     = (String)namespace.invoke(sym);
    symName   = (String)name.invoke(sym);

    require.invoke(Clojure.read(symNs));
    handler = Clojure.var(symNs, symName);
  }

  public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
    System.out.println("Invoking "+sym.toString());
    handler.invoke(input, output, context);
  }
}
