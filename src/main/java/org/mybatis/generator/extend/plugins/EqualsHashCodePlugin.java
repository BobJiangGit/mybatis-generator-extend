package org.mybatis.generator.extend.plugins;

import java.util.Iterator;
import java.util.List;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.util.JavaBeansUtil;

/**
 * 重写equals方法和hashCode方法
 *
 * Created by Bob Jiang on 2017/2/10.
 */
public class EqualsHashCodePlugin extends PluginAdapter {

    public boolean validate(List<String> warnings) {
        return true;
    }

    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        List columns;
        if(introspectedTable.getRules().generateRecordWithBLOBsClass()) {
            columns = introspectedTable.getNonBLOBColumns();
        } else {
            columns = introspectedTable.getPrimaryKeyColumns();
        }

        this.generateEquals(topLevelClass, columns, introspectedTable);
        this.generateHashCode(topLevelClass, columns, introspectedTable);
        return true;
    }

    public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        this.generateEquals(topLevelClass, introspectedTable.getPrimaryKeyColumns(), introspectedTable);
        this.generateHashCode(topLevelClass, introspectedTable.getPrimaryKeyColumns(), introspectedTable);
        return true;
    }

    public boolean modelRecordWithBLOBsClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        this.generateEquals(topLevelClass, introspectedTable.getAllColumns(), introspectedTable);
        this.generateHashCode(topLevelClass, introspectedTable.getAllColumns(), introspectedTable);
        return true;
    }

    protected void generateEquals(TopLevelClass topLevelClass, List<IntrospectedColumn> introspectedColumns, IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(FullyQualifiedJavaType.getBooleanPrimitiveInstance());
        method.setName("equals");
        method.addParameter(new Parameter(FullyQualifiedJavaType.getObjectInstance(), "that"));
        if(introspectedTable.isJava5Targeted()) {
            method.addAnnotation("@Override");
        }

        this.context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        method.addBodyLine("if (this == that) {");
        method.addBodyLine("return true;");
        method.addBodyLine("}");
        method.addBodyLine("if (that == null) {");
        method.addBodyLine("return false;");
        method.addBodyLine("}");
        method.addBodyLine("if (getClass() != that.getClass()) {");
        method.addBodyLine("return false;");
        method.addBodyLine("}");
        StringBuilder sb = new StringBuilder();
        sb.append(topLevelClass.getType().getShortName());
        sb.append(" other = (");
        sb.append(topLevelClass.getType().getShortName());
        sb.append(") that;");
        method.addBodyLine(sb.toString());
        boolean first = true;

        for(Iterator iter = introspectedColumns.iterator(); iter.hasNext(); method.addBodyLine(sb.toString())) {
            IntrospectedColumn introspectedColumn = (IntrospectedColumn)iter.next();
            sb.setLength(0);
            if(first) {
                sb.append("return (");
                first = false;
            } else {
                OutputUtilities.javaIndent(sb, 1);
                sb.append("&& (");
            }

            String getterMethod = JavaBeansUtil.getGetterMethodName(introspectedColumn.getJavaProperty(), introspectedColumn.getFullyQualifiedJavaType());
            if(introspectedColumn.getFullyQualifiedJavaType().isPrimitive()) {
                sb.append("this.");
                sb.append(getterMethod);
                sb.append("() == ");
                sb.append("other.");
                sb.append(getterMethod);
                sb.append("())");
            } else {
                sb.append("this.");
                sb.append(getterMethod);
                sb.append("() == null ? other.");
                sb.append(getterMethod);
                sb.append("() == null : this.");
                sb.append(getterMethod);
                sb.append("().equals(other.");
                sb.append(getterMethod);
                sb.append("()))");
            }

            if(!iter.hasNext()) {
                sb.append(';');
            }
        }

        topLevelClass.addMethod(method);
    }

    protected void generateHashCode(TopLevelClass topLevelClass, List<IntrospectedColumn> introspectedColumns, IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.setName("hashCode");
        if(introspectedTable.isJava5Targeted()) {
            method.addAnnotation("@Override");
        }

        this.context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        method.addBodyLine("final int prime = 31;");
        method.addBodyLine("int result = 1;");
        StringBuilder sb = new StringBuilder();
        boolean hasTemp = false;
        Iterator iter = introspectedColumns.iterator();

        while(iter.hasNext()) {
            IntrospectedColumn introspectedColumn = (IntrospectedColumn)iter.next();
            FullyQualifiedJavaType fqjt = introspectedColumn.getFullyQualifiedJavaType();
            String getterMethod = JavaBeansUtil.getGetterMethodName(introspectedColumn.getJavaProperty(), fqjt);
            sb.setLength(0);
            if(fqjt.isPrimitive()) {
                if("boolean".equals(fqjt.getFullyQualifiedName())) {
                    sb.append("result = prime * result + (");
                    sb.append(getterMethod);
                    sb.append("() ? 1231 : 1237);");
                    method.addBodyLine(sb.toString());
                } else if("byte".equals(fqjt.getFullyQualifiedName())) {
                    sb.append("result = prime * result + ");
                    sb.append(getterMethod);
                    sb.append("();");
                    method.addBodyLine(sb.toString());
                } else if("char".equals(fqjt.getFullyQualifiedName())) {
                    sb.append("result = prime * result + ");
                    sb.append(getterMethod);
                    sb.append("();");
                    method.addBodyLine(sb.toString());
                } else if("double".equals(fqjt.getFullyQualifiedName())) {
                    if(!hasTemp) {
                        method.addBodyLine("long temp;");
                        hasTemp = true;
                    }

                    sb.append("temp = Double.doubleToLongBits(");
                    sb.append(getterMethod);
                    sb.append("());");
                    method.addBodyLine(sb.toString());
                    method.addBodyLine("result = prime * result + (int) (temp ^ (temp >>> 32));");
                } else if("float".equals(fqjt.getFullyQualifiedName())) {
                    sb.append("result = prime * result + Float.floatToIntBits(");
                    sb.append(getterMethod);
                    sb.append("());");
                    method.addBodyLine(sb.toString());
                } else if("int".equals(fqjt.getFullyQualifiedName())) {
                    sb.append("result = prime * result + ");
                    sb.append(getterMethod);
                    sb.append("();");
                    method.addBodyLine(sb.toString());
                } else if("long".equals(fqjt.getFullyQualifiedName())) {
                    sb.append("result = prime * result + (int) (");
                    sb.append(getterMethod);
                    sb.append("() ^ (");
                    sb.append(getterMethod);
                    sb.append("() >>> 32));");
                    method.addBodyLine(sb.toString());
                } else if("short".equals(fqjt.getFullyQualifiedName())) {
                    sb.append("result = prime * result + ");
                    sb.append(getterMethod);
                    sb.append("();");
                    method.addBodyLine(sb.toString());
                }
            } else {
                sb.append("result = prime * result + ((");
                sb.append(getterMethod);
                sb.append("() == null) ? 0 : ");
                sb.append(getterMethod);
                sb.append("().hashCode());");
                method.addBodyLine(sb.toString());
            }
        }

        method.addBodyLine("return result;");
        topLevelClass.addMethod(method);
    }
}
