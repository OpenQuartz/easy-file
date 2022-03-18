package org.svnee.easyfile.starter.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.svnee.easyfile.common.annotation.FileExportExecutor;
import org.svnee.easyfile.common.util.SpringContextUtil;
import org.svnee.easyfile.starter.executor.BaseDownloadExecutor;

/**
 * 异步文件执行
 *
 * @author svnee
 */
public class FileExportExecutorPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        Class<?> clazz = SpringContextUtil.getRealClass(bean);

        if (clazz.isAnnotationPresent(FileExportExecutor.class)) {
            if (!BaseDownloadExecutor.class.isAssignableFrom(clazz)) {
                throw new RuntimeException(String.format("%s must implement %s", clazz, BaseDownloadExecutor.class));
            }
            FileExportExecutor exportExecutor = clazz.getAnnotation(FileExportExecutor.class);
            if (FileExportExecutorSupport.contains(exportExecutor.value())
                && !clazz.equals(FileExportExecutorSupport.get(exportExecutor.value()).getClass())) {
                throw new RuntimeException(
                    String.format("downloadCode:(%s) not unique,please check it!", exportExecutor.value()));
            }
            FileExportExecutorSupport.register(exportExecutor.value(), exportExecutor, (BaseDownloadExecutor) bean);
        }
        return bean;
    }
}
