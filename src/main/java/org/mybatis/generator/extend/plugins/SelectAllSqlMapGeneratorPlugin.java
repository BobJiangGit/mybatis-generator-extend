package org.mybatis.generator.extend.plugins;

import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.xml.*;
import java.util.List;

/**
 * Created by Bob Jiang on 2017/2/13.
 */
public class SelectAllSqlMapGeneratorPlugin extends PluginAdapter {

    public boolean validate(List<String> list) {
        return true;
    }

    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        String tableName = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();
        XmlElement parentElement = document.getRootElement();

//        try {
//            String packageName = introspectedTable.getFullyQualifiedTable().getSubPackage(true);
//            SqlMapGeneratorConfiguration configuration = introspectedTable.getContext().getSqlMapGeneratorConfiguration();
//
//            String fileName = introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Mapper.xml";
//            File mapperDir = this.shellCallback.getDirectory(configuration.getTargetProject(), configuration.getTargetPackage() + "." + packageName);
//            File mapperFile = new File(mapperDir, fileName);
//            if(mapperFile.exists()) {
////                return true;
//            }
//        } catch (ShellException e) {
//            e.printStackTrace();
//        }

        // 生成查询语句Element
        XmlElement selectAllElement = new XmlElement("select");
        selectAllElement.addAttribute(new Attribute("id", "selectAll"));
        selectAllElement.addAttribute(new Attribute("resultMap", "BaseResultMap"));

        String sql = "select <include refid=\"Base_Column_List\" /> from " + tableName;
        selectAllElement.addElement(new TextElement(sql));
        parentElement.addElement(selectAllElement);
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }

}
