/**    
 * 文件名：ReadProperties.java    
 *    
 * 版本信息：    
 * 日期：2013-7-1    
 * Copyright 厦门摩易信息科技有限公司 2013     
 * 版权所有  闽ICP备05026517号   
 *    
 */

package com.dianfeng.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 
 * 项目名称：MCPServer 类名称：ReadProperties 类描述： 创建人：wystbs 创建时间：2013-7-1 下午02:24:17
 * 修改人：wystbs 修改时间：2013-7-1 下午02:24:17 修改备注：
 * 
 * @version
 * 
 */
public class ReadProperties {

    /**
     * 
     * read(读取properties)
     * 
     * TODO(这里描述这个方法适用条件 – 可选)
     * 
     * TODO(这里描述这个方法的执行流程 – 可选)
     * 
     * TODO(这里描述这个方法的使用方法 – 可选)
     * 
     * TODO(这里描述这个方法的注意事项 – 可选)
     * 
     * @param name
     * 
     * @param @return 设定文件
     * 
     * @return String DOM对象
     * 
     * @Exception 异常对象
     * 
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    public String read(String arg) {
        ReadProperties loadProp = new ReadProperties();
        InputStream in = loadProp.getClass().getResourceAsStream("/com/dianfeng/utils/config.properties");
        Properties prop = new Properties();
        try {
            prop.load(new InputStreamReader(in, "UTF-8"));
        } catch (IOException ioex) {
            System.err.println("读取ReadProperties失败" + ioex.toString());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ioex2) {
                    System.err.println(this.getClass().getName() + ".mymethod - ReadProperties关闭失败" + ioex2.toString());
                }
            }

        }
        String value = null;
        value = prop.getProperty(arg, "none");
        System.out.println(value);
        return value;
    }
}
