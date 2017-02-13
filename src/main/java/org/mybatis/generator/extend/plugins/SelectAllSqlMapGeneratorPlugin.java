package org.mybatis.generator.extend.plugins;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

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
        String alias = introspectedTable.getFullyQualifiedTable().getAlias();
        XmlElement parentElement = document.getRootElement();

        // 产生分页语句前半部分
        XmlElement selectAllElement = new XmlElement("select");
        selectAllElement.addAttribute(new Attribute("id", "selectAll"));
        selectAllElement.addAttribute(new Attribute("resultMap", "BaseResultMap"));

        String sql = "select <include refid=\"Base_Column_List\" /> from " + tableName + " " + alias;
        selectAllElement.addElement(new TextElement(sql));
        parentElement.addElement(selectAllElement);
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }
}
