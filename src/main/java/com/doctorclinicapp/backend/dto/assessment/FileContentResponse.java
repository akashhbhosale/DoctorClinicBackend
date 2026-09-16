package com.doctorclinicapp.backend.dto.assessment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Wraps the raw bytes of an uploaded report file plus enough metadata
 * (content type, original file name) for a controller to stream it back
 * with the right headers for inline viewing or download. Shared by both
 * Laboratory and Radiology file-serving endpoints.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileContentResponse {

    private byte[] data;
    private String contentType;
    private String fileName;
}
