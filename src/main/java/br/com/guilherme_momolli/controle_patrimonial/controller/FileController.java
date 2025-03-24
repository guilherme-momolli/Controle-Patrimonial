package br.com.guilherme_momolli.controle_patrimonial.controller;
import br.com.guilherme_momolli.controle_patrimonial.service.FileStorageService;
import br.com.guilherme_momolli.controle_patrimonial.vo.UploadFileResponseVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@CrossOrigin
@RestController
@RequestMapping("file/v1")
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;

    private static final Logger logger = Logger.getLogger(FileController.class.getName());

    private static final List<String> ALLOWED_FILE_TYPES = Arrays.asList("image/jpeg",
            "image/png",
            "application/pdf",
            "image/gif",
            "image/tiff",
            "image/bmp");

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (Exception ex) {
            logger.info("Could not determine file type.");
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header("Content-Disposition", "attachment; filename=\""
                        + resource.getFilename()
                        + "\"").body(resource);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, path = "/uploadFile")
    public UploadFileResponseVO uploadFile(@RequestParam("file") MultipartFile file) {
        if (!ALLOWED_FILE_TYPES.contains(file.getContentType())) {
            throw new RuntimeException("Invalid file type.");
        }
        String fileName = fileStorageService.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/file/v1/downloadFile/").path(fileName).toUriString();


        return new UploadFileResponseVO(fileName, fileDownloadUri, file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponseVO> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        List<UploadFileResponseVO> responses = new ArrayList<>();
        for (MultipartFile file : files) {
            responses.add(uploadFile(file));
        }
        return responses;
    }


}

