package org.jboss.resteasy.core;

import org.jboss.resteasy.core.interception.InterceptorRegistry;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.jboss.resteasy.spi.StringConverter;
import org.jboss.resteasy.util.ThreadLocalStack;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Variant;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Allow applications to push/pop provider factories onto the stack
 *
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class ThreadLocalResteasyProviderFactory extends ResteasyProviderFactory
{
   private static final ThreadLocalStack<ResteasyProviderFactory> delegate = new ThreadLocalStack<ResteasyProviderFactory>();

   private ResteasyProviderFactory defaultFactory;

   public ThreadLocalResteasyProviderFactory(ResteasyProviderFactory defaultFactory)
   {
      this.defaultFactory = defaultFactory;
   }

   public ResteasyProviderFactory getDelegate()
   {
      ResteasyProviderFactory factory = delegate.get();
      if (factory == null) return defaultFactory;
      return factory;
   }

   public static void push(ResteasyProviderFactory factory)
   {
      delegate.push(factory);
   }

   public static void pop()
   {
      delegate.pop();
   }

   @Override
   public UriBuilder createUriBuilder()
   {
      return getDelegate().createUriBuilder();
   }

   @Override
   public Response.ResponseBuilder createResponseBuilder()
   {
      return getDelegate().createResponseBuilder();
   }

   @Override
   public Variant.VariantListBuilder createVariantListBuilder()
   {
      return getDelegate().createVariantListBuilder();
   }

   @Override
   public <T> HeaderDelegate<T> createHeaderDelegate(Class<T> tClass)
   {
      return getDelegate().createHeaderDelegate(tClass);
   }

   @Override
   public void addHeaderDelegate(Class clazz, HeaderDelegate header)
   {
      getDelegate().addHeaderDelegate(clazz, header);
   }

   @Override
   public void addMessageBodyReader(Class<? extends MessageBodyReader> provider)
   {
      getDelegate().addMessageBodyReader(provider);
   }

   @Override
   public void addMessageBodyReader(MessageBodyReader provider)
   {
      getDelegate().addMessageBodyReader(provider);
   }

   @Override
   public void addMessageBodyWriter(Class<? extends MessageBodyWriter> provider)
   {
      getDelegate().addMessageBodyWriter(provider);
   }

   @Override
   public void addMessageBodyWriter(MessageBodyWriter provider)
   {
      getDelegate().addMessageBodyWriter(provider);
   }

   @Override
   public <T> MessageBodyReader<T> getMessageBodyReader(Class<T> type, Type genericType, Annotation[] annotations, MediaType mediaType)
   {
      return getDelegate().getMessageBodyReader(type, genericType, annotations, mediaType);
   }

   @Override
   public void addExceptionMapper(Class<? extends ExceptionMapper> provider)
   {
      getDelegate().addExceptionMapper(provider);
   }

   @Override
   public void addExceptionMapper(ExceptionMapper provider)
   {
      getDelegate().addExceptionMapper(provider);
   }

   @Override
   public void addContextResolver(Class<? extends ContextResolver> resolver)
   {
      getDelegate().addContextResolver(resolver);
   }

   @Override
   public void addContextResolver(ContextResolver provider)
   {
      getDelegate().addContextResolver(provider);
   }

   @Override
   public void addStringConverter(Class<? extends StringConverter> resolver)
   {
      getDelegate().addStringConverter(resolver);
   }

   @Override
   public void addStringConverter(StringConverter provider)
   {
      getDelegate().addStringConverter(provider);
   }

   @Override
   public List<ContextResolver> getContextResolvers(Class<?> clazz, MediaType type)
   {
      return getDelegate().getContextResolvers(clazz, type);
   }

   @Override
   public StringConverter getStringConverter(Class<?> clazz)
   {
      return getDelegate().getStringConverter(clazz);
   }

   @Override
   public void registerProvider(Class provider)
   {
      getDelegate().registerProvider(provider);
   }

   @Override
   public void registerProviderInstance(Object provider)
   {
      getDelegate().registerProviderInstance(provider);
   }

   @Override
   public <T> T getProvider(Class<T> providerClass)
   {
      return getDelegate().getProvider(providerClass);
   }

   @Override
   public <T extends Throwable> ExceptionMapper<T> getExceptionMapper(Class<T> type)
   {
      return getDelegate().getExceptionMapper(type);
   }

   @Override
   public <T> MessageBodyWriter<T> getMessageBodyWriter(Class<T> type, Type genericType, Annotation[] annotations, MediaType mediaType)
   {
      return getDelegate().getMessageBodyWriter(type, genericType, annotations, mediaType);
   }

   @Override
   public <T> T createEndpoint(Application applicationConfig, Class<T> endpointType)
           throws IllegalArgumentException, UnsupportedOperationException
   {
      return getDelegate().createEndpoint(applicationConfig, endpointType);
   }

   @Override
   public InterceptorRegistry getInterceptorRegistry()
   {
      return getDelegate().getInterceptorRegistry();
   }

   @Override
   public <T> ContextResolver<T> getContextResolver(Class<T> contextType, MediaType mediaType)
   {
      return getDelegate().getContextResolver(contextType, mediaType);
   }
}