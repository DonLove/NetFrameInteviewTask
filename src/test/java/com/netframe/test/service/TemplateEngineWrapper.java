
package com.netframe.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.IContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

/**
 *
 * @author liubov
 */
@Component
public class TemplateEngineWrapper {
    
    private SpringTemplateEngine templateEngine;

    public String process(String templateName, IContext context) {
        return templateEngine.process(templateName, context);
    }

    public TemplateEngineWrapper(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }
    
    
}
