package net.javaguides.springboot.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="server_backups")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Server_Backups {
	@Id
	private Long backup_id;
	private Long server_id;
	

}
