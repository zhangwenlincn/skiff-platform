package com.skiff.app.test.file;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/file")
public class FileController {


    @PostMapping("/download")
    public void download(@RequestBody FilePram filePram, HttpServletResponse response) throws Exception {

        response.setContentType("application/octet-stream");


        //response.getOutputStream().write(bytes);
        //response.flushBuffer();
    }

}
