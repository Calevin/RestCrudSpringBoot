package com.example.demo.modelos;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.seguridad.RolUsuario;

@Entity
@Table(name = "USUARIOS")
@EntityListeners(AuditingEntityListener.class)
public class Usuario implements UserDetails {

	private static final long serialVersionUID = -493314352906801523L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = true)
	private String username;

	private String password;

	private String avatar;

	@CreatedDate
	@Column(name = "fecha_creacion")
	private LocalDateTime fechaCreacion;

	@Column(name = "fecha_ultima_modificacion_password")
	private LocalDateTime fechaUltimaModificacionPassword = LocalDateTime.now();

	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	private Set<RolUsuario> roles;

	public Usuario() {
		super();
	}

	public Usuario(Long id, String username, String password, String avatar, LocalDateTime fechaCreacion,
			LocalDateTime fechaUltimaModificacionPassword, Set<RolUsuario> roles) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.avatar = avatar;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaModificacionPassword = fechaUltimaModificacionPassword;
		this.roles = roles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles.stream()
				.map(ur -> new SimpleGrantedAuthority("ROLE_" + ur.name()))
				.collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public LocalDateTime getFechaUltimaModificacionPassword() {
		return fechaUltimaModificacionPassword;
	}

	public void setFechaUltimaModificacionPassword(LocalDateTime fechaUltimaModificacionPassword) {
		this.fechaUltimaModificacionPassword = fechaUltimaModificacionPassword;
	}

	public Set<RolUsuario> getRoles() {
		return roles;
	}

	public void setRoles(Set<RolUsuario> roles) {
		this.roles = roles;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
