package com.scut.backend.core;

import org.springframework.stereotype.Component;

@Component
public class ScanCodeModule {

    public void execute(String path) {
        com.sun.tools.javadoc.Main.execute(new String[] {"-doclet",
                        MyDoclet.class.getName(),
// 因为自定义的Doclet类并不在外部jar中，就在当前类中，所以这里不需要指定-docletpath 参数，
//				"-docletpath",
//				Doclet.class.getResource("/").getPath(),
                        "-encoding","utf-8",
                "-classpath",
// 获取单个代码文件FaceLogDefinition.java的javadoc
                "",
                path});
    }

    public static void main(String[] args) {
        ScanCodeModule module = new ScanCodeModule();
        module.execute("F:\\graduate\\code\\ApiDocManage\\backend\\upload\\3000\\root\\AdminController.java");
    }
}
