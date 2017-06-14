/*     */ package com.eauto100.pub.service.framework.context;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Collection;
/*     */ import java.util.Locale;
/*     */ import javax.servlet.ServletOutputStream;
/*     */ import javax.servlet.http.Cookie;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ 
/*     */ public class SafeHttpResponseWraper implements HttpServletResponse
/*     */ {
/*     */   private HttpServletResponse response;
/*     */   
/*     */   public SafeHttpResponseWraper(HttpServletResponse _response)
/*     */   {
/*  17 */     this.response = _response;
/*     */   }
/*     */   
/*     */   public void flushBuffer() throws IOException {
/*  21 */     this.response.flushBuffer();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getBufferSize()
/*     */   {
/*  28 */     return this.response.getBufferSize();
/*     */   }
/*     */   
/*     */ 
/*     */   public String getCharacterEncoding()
/*     */   {
/*  34 */     return this.response.getCharacterEncoding();
/*     */   }
/*     */   
/*     */ 
/*     */   public String getContentType()
/*     */   {
/*  40 */     return this.response.getContentType();
/*     */   }
/*     */   
/*     */ 
/*     */   public Locale getLocale()
/*     */   {
/*  46 */     return this.response.getLocale();
/*     */   }
/*     */   
/*     */   public ServletOutputStream getOutputStream()
/*     */     throws IOException
/*     */   {
/*  52 */     return this.response.getOutputStream();
/*     */   }
/*     */   
/*     */   public PrintWriter getWriter() throws IOException
/*     */   {
/*  57 */     PrintWriter writer = this.response.getWriter();
/*  58 */     //String content = TplUtil.process();
///*  59 */     if ((content != null) && (!content.equals(""))) {
///*  60 */       writer.append(content);
///*     */     }
/*  62 */     return writer;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isCommitted()
/*     */   {
/*  68 */     return this.response.isCommitted();
/*     */   }
/*     */   
/*     */   public void reset()
/*     */   {
/*  73 */     this.response.reset();
/*     */   }
/*     */   
/*     */ 
/*     */   public void resetBuffer()
/*     */   {
/*  79 */     this.response.resetBuffer();
/*     */   }
/*     */   
/*     */ 
/*     */   public void setBufferSize(int arg0)
/*     */   {
/*  85 */     this.response.setBufferSize(arg0);
/*     */   }
/*     */   
/*     */ 
/*     */   public void setCharacterEncoding(String arg0)
/*     */   {
/*  91 */     this.response.setCharacterEncoding(arg0);
/*     */   }
/*     */   
/*     */ 
/*     */   public void setContentLength(int arg0)
/*     */   {
/*  97 */     this.response.setContentLength(arg0);
/*     */   }
/*     */   
/*     */ 
/*     */   public void setContentType(String arg0)
/*     */   {
/* 103 */     this.response.setContentType(arg0);
/*     */   }
/*     */   
/*     */ 
/*     */   public void setLocale(Locale arg0)
/*     */   {
/* 109 */     this.response.setLocale(arg0);
/*     */   }
/*     */   
/*     */ 
/*     */   public void addCookie(Cookie arg0)
/*     */   {
/* 115 */     this.response.addCookie(arg0);
/*     */   }
/*     */   
/*     */ 
/*     */   public void addDateHeader(String arg0, long arg1)
/*     */   {
/* 121 */     this.response.addDateHeader(arg0, arg1);
/*     */   }
/*     */   
/*     */ 
/*     */   public void addHeader(String arg0, String arg1)
/*     */   {
/* 127 */     this.response.addHeader(arg0, arg1);
/*     */   }
/*     */   
/*     */ 
/*     */   public void addIntHeader(String arg0, int arg1)
/*     */   {
/* 133 */     this.response.addIntHeader(arg0, arg1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean containsHeader(String arg0)
/*     */   {
/* 140 */     return this.response.containsHeader(arg0);
/*     */   }
/*     */   
/*     */ 
/*     */   public String encodeRedirectURL(String arg0)
/*     */   {
/* 146 */     return this.response.encodeRedirectURL(arg0);
/*     */   }
/*     */   
/*     */ 
/*     */   public String encodeRedirectUrl(String arg0)
/*     */   {
/* 152 */     return this.response.encodeRedirectUrl(arg0);
/*     */   }
/*     */   
/*     */ 
/*     */   public String encodeURL(String arg0)
/*     */   {
/* 158 */     return this.response.encodeURL(arg0);
/*     */   }
/*     */   
/*     */ 
/*     */   public String encodeUrl(String arg0)
/*     */   {
/* 164 */     return this.response.encodeUrl(arg0);
/*     */   }
/*     */   
/*     */   public void sendError(int arg0) throws IOException
/*     */   {
/* 169 */     this.response.sendError(arg0);
/*     */   }
/*     */   
/*     */   public void sendError(int arg0, String arg1)
/*     */     throws IOException
/*     */   {
/* 175 */     this.response.sendError(arg0, arg1);
/*     */   }
/*     */   
/*     */   public void sendRedirect(String arg0)
/*     */     throws IOException
/*     */   {
/* 181 */     this.response.sendRedirect(arg0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setDateHeader(String arg0, long arg1)
/*     */   {
/* 188 */     this.response.setDateHeader(arg0, arg1);
/*     */   }
/*     */   
/*     */   public void setHeader(String arg0, String arg1)
/*     */   {
/* 193 */     this.response.setHeader(arg0, arg1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setIntHeader(String arg0, int arg1)
/*     */   {
/* 200 */     this.response.setIntHeader(arg0, arg1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setStatus(int arg0)
/*     */   {
/* 207 */     this.response.setStatus(arg0);
/*     */   }
/*     */   
/*     */   public void setStatus(int arg0, String arg1)
/*     */   {
/* 212 */     this.response.setStatus(arg0, arg1);
/*     */   }
/*     */   
/*     */ 
/*     */   public String getHeader(String arg0)
/*     */   {
/* 218 */     return this.response.getHeader(arg0);
/*     */   }
/*     */   
/*     */   public void setContentLengthLong(long arg0)
/*     */   {
/* 223 */     this.response.setContentLength(Long.valueOf(arg0).intValue());
/*     */   }
/*     */   
/*     */   public Collection<String> getHeaderNames()
/*     */   {
/* 228 */     return this.response.getHeaderNames();
/*     */   }
/*     */   
/*     */   public Collection<String> getHeaders(String arg0) {
/* 232 */     return this.response.getHeaders(arg0);
/*     */   }
/*     */   
/*     */   public int getStatus()
/*     */   {
/* 237 */     return this.response.getStatus();
/*     */   }
/*     */ }


/* Location:              E:\Downloads\apache-template.jar!\io\apache\org\template\SafeHttpResponseWraper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */