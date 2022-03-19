package org.svnee.easyfile.starter.executor.impl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import javax.validation.groups.Default;
import org.apache.commons.lang3.tuple.Pair;
import org.svnee.easyfile.common.bean.DownloaderRequestContext;
import org.svnee.easyfile.common.bean.Page;
import org.svnee.easyfile.common.bean.PageTotal;
import org.svnee.easyfile.common.bean.PageTotalContext;
import org.svnee.easyfile.common.bean.excel.ExcelBean;
import org.svnee.easyfile.common.bean.excel.ExcelBeanUtils;
import org.svnee.easyfile.common.bean.excel.ExcelExports;
import org.svnee.easyfile.common.util.GenericUtils;
import org.svnee.easyfile.common.util.PageUtil;
import org.svnee.easyfile.starter.executor.PageShardingDownloadExecutor;

/**
 * 分页下载Excel执行器
 * 主要用于大 Excel 文件的导出。目前仅支持07版excel
 * 同时 <T> 泛型为导出实体类对象
 * 对象导出需要配合注解使用{@link org.svnee.easyfile.common.annotation.ExcelProperty}
 *
 * @author svnee
 */
public abstract class AbstractPageDownloadExcelExecutor<T> implements PageShardingDownloadExecutor<T> {

    /**
     * 导出模板类分组 {@link org.svnee.easyfile.common.annotation.ExcelProperty#group()}
     *
     * @param context context
     * @return Class<?>
     */
    public Class<?>[] exportGroup(DownloaderRequestContext context) {
        return new Class<?>[]{Default.class};
    }

    /**
     * 导出实现
     */
    @Override
    public void export(DownloaderRequestContext context) {
        PageTotal total;
        if (PageTotalContext.currentPageToTal() != null) {
            total = PageTotalContext.currentPageToTal();
        } else {
            // 执行 PageTotal 查询
            total = count(context.getOtherMap());
        }
        ExcelBean excelBean = ExcelExports.createWorkbook();
        try {
            List<Field> fieldList = ExcelBeanUtils
                .getFieldsByGroup(GenericUtils.getClassT(this, 0), exportGroup(context));

            if (total.getTotal() <= 0) {
                // 无结果导出
                ExcelExports.writeHeader(excelBean, fieldList);
                ExcelExports.writeWorkbook(excelBean, context.getOut());
                return;
            }
            int totalPage = PageUtil.totalPage(total.getTotal(), total.getPageSize());
            Page page = new Page(1, total.getPageSize(), total.getTotal());
            Long maxId = 0L;
            for (int i = 0; i < totalPage; i++) {
                page.setPageNum(i + 1);
                Pair<Long, List<T>> pair = shardingData(context, page, maxId);
                if (Objects.nonNull(pair)) {
                    maxId = pair.getKey();
                    ExcelExports.writeData(excelBean, fieldList, pair.getValue());
                }
            }
            ExcelExports.writeWorkbook(excelBean, context.getOut());
        } finally {
            excelBean.close();
        }
    }

}