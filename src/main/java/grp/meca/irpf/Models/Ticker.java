/*
 * Esta classe serve de ajuda para lançar os dados no programa da Receita Federal
 * no campo CNPJ em "Bens e Direitos".
 */

package grp.meca.irpf.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity(name = "tickers")
public class Ticker {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private String codigo;
	
	@Column(nullable = false)
	private String cnpj;
	
	@Column(nullable = false)
	private boolean bdr;
	
	public Ticker() {
		this("fake3", "00000000000000", false);
	}
	
	public Ticker(int id, String codigo, String cnpj, boolean bdr) {
		this.id = id;
		this.codigo = codigo;
		if(cnpj == null || cnpj.equals(""))
			this.cnpj = "00000000000000";
		else
			this.cnpj = cnpj;
		this.bdr = bdr;
	}

	public Ticker(String codigo, String cnpj, boolean bdr) {
		super();
		this.codigo = codigo;
		if(cnpj == null || cnpj.equals(""))
			this.cnpj = "00000000000000";
		else
			this.cnpj = cnpj;
		this.bdr = bdr;
	}

	public Ticker(String codigo, boolean bdr) {
		this(codigo, "00000000000000", bdr);
	}
	
	public Ticker(String codigo) {
		this(codigo, "00000000000000", false);
	}
	
}
