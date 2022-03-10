package pers.prover07.crowd.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author by Prover07
 * @classname PortalProjectVo
 * @description TODO
 * @date 2022/3/10 12:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortalProjectVo {

    private Integer projectId;
    private String projectName;
    private String headerPicturePath;
    private Integer money;
    private String deployDate;
    private Integer percentage;
    private Integer supporter;

}
