package com.prebel.prototipo.webapp.models.role_module;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.prebel.prototipo.webapp.models.weekly_planner.TechnicianSchedule;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"weeklyTasks"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String password;
    private String name;
    private String number;
    private String email;
    private String resetCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "technician", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("technician")
    private List<TechnicianSchedule> weeklyTasks;

}
