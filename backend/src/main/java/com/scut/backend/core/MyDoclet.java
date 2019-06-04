package com.scut.backend.core;

import com.sun.javadoc.Doclet;
import com.sun.javadoc.RootDoc;

public class MyDoclet extends Doclet {

    private static RootDoc rootdoc;

    public static RootDoc getRootDoc() {
        return rootdoc;
    }

    public static boolean start(RootDoc root) {
        System.out.println("success");
        rootdoc = root;
        return true;
    }

    public static void execute(String path) {
        com.sun.tools.javadoc.Main.execute(new String[]{"-doclet",
                MyDoclet.class.getName(),
// 因为自定义的Doclet类并不在外部jar中，就在当前类中，所以这里不需要指定-docletpath 参数，
//				"-docletpath",
//				Doclet.class.getResource("/").getPath(),
                "-encoding", "utf-8",
                "-classpath",
// 获取单个代码文件FaceLogDefinition.java的javadoc
                "",
                path});
    }
}