/*
 * Cada nota de corretagem tem uma lista de ordens, uma data e um valor líquido.
 * O valor bruto pode ser calculado a partir das ordens. O mesmo pode ser dito
 * para as taxas a partir da diferença do valor bruto e o líquido.
 */

package grp.meca.irpf.Models;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity(name = "notas_de_corretagem")
public class NotaDeCorretagem implements Comparable<NotaDeCorretagem> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private LocalDate data;
	
	@Column(nullable = false)
	private double valorLiquido;
	
	@Transient
	private List<Ordem> ordens;
	
	public NotaDeCorretagem() {}
	
	public NotaDeCorretagem(LocalDate data, double valorLiquido) {
		this.data = data;
		this.valorLiquido = valorLiquido;
	}
	
	@Override
	public int compareTo(NotaDeCorretagem nc) {
		return this.getData().compareTo(nc.getData());
	}

}
