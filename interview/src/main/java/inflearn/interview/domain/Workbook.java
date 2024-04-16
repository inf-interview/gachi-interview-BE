package inflearn.interview.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Getter
@Entity
public class Workbook {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users user;

    private String title;

    public void setUser(Users user) {
        this.user = user;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
