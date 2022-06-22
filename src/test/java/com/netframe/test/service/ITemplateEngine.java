
package com.netframe.test.service;

import org.thymeleaf.context.IContext;

/**
 *
 * @author liubov
 */
public interface ITemplateEngine {
    String process(String template, IContext context);
}
