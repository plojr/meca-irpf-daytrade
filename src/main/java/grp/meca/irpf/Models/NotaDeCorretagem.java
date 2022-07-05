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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity(name = "notas_de_corretagem")
public class NotaDeCorretagem implements Comparable<NotaDeCorretagem> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private LocalDate date;
	
	@Column(nullable = false)
	private double valorLiquido;
	
	@Transient
	private List<Ordem> ordens;
	
	public NotaDeCorretagem() {}
	
	public NotaDeCorretagem(LocalDate date, double valorLiquido) {
		this.date = date;
		this.valorLiquido = valorLiquido;
	}
	
	// Valor bruto pode ser negativo, desde que as compras superem as vendas.
	// Será positivo caso contrário.
	@JsonIgnore
	public double getValorBruto() {
		double value = 0.0;
		for(Ordem ordem: ordens) {
			if(ordem.getTipo() == 'c')
				value -= ordem.getQuantidade()*ordem.getPreco();
			else
				value += ordem.getQuantidade()*ordem.getPreco();
		}
		return value;
	}

	// Apesar de as taxas serem algo que devemos pagar, neste código, 
	// as considero com valor positivo.
	@JsonIgnore
	public double getTaxas() {
		return getValorBruto() - getValorLiquido();
	}
	
	public double getTaxaDaOrdem(Ordem ordem) {
		return getTaxaDaOrdem(ordem.getPreco()*ordem.getQuantidade());
	}
	
	public double getTaxaDaOrdem(double valorDeOrdem) {
		double valorAbsolutoDaCorretagem = 0;
		for(Ordem ordem: ordens)
			valorAbsolutoDaCorretagem += ordem.getPreco()*ordem.getQuantidade();
		return getTaxas()*valorDeOrdem/valorAbsolutoDaCorretagem;
	}

	@Override
	public int compareTo(NotaDeCorretagem nc) {
		return this.getDate().compareTo(nc.getDate());
	}

}
