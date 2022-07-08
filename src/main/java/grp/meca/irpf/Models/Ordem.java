/*
 * Esta classe é a parte mais básica. Cada compra ou venda feita é gerada um registro no banco relativo à esta classe.
 * Uma ordem é do tipo "compra de 100 itsa4 ao preço de R$ 10.00" ou "venda de 100 petr3 ao preço de R$ 30.00".
 * A variável tipo será 'c' para compra ou 'v' para venda.
 */

package grp.meca.irpf.Models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity(name = "ordens")
public class Ordem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private char tipo;
	
	@Column(nullable = false)
	private int quantidade;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ticker_id", nullable = false)
	private Ticker ticker;
	
	@Column(nullable = false)
	private double preco;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "corretagem_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private NotaDeCorretagem notaDeCorretagem;
	
	public Ordem() {}
	
	public Ordem(char tipo, int quantidade, Ticker ticker, double preco, NotaDeCorretagem notaDeCorretagem) throws Exception {
		if(tipo != 'c' && tipo != 'v') throw new Exception("Tipo de ordem inválido: " + tipo);
		this.tipo = tipo;
		if(quantidade <= 0) throw new Exception("Quantidade de ordem inválida: " + quantidade);
		this.quantidade = quantidade;
		this.ticker = ticker;
		if(preco <= 0) throw new Exception("Preço de ordem inválido: " + preco);
		this.preco = preco;
		this.notaDeCorretagem = notaDeCorretagem;
	}
	
}
