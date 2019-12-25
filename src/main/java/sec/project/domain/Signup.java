package sec.project.domain;


import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Signup extends AbstractPersistable<Long> {

//    @Column(unique = true)
    private String name;
    private String password;
    private String role1 = "user";
 
    
        public Signup() {
            

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void setRole(String role) {
        this.role1 = role;
    }
    public String getRole(){
        return role1;
    }
    

}