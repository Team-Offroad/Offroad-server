package site.offload.dbjpa.member.embeddable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Birthday {

    private Integer yearValue;
    private Integer monthValue;
    private Integer dayValue;
}
