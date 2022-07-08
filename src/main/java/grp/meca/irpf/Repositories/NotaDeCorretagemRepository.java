package grp.meca.irpf.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import grp.meca.irpf.Models.NotaDeCorretagem;

@Repository
public interface NotaDeCorretagemRepository extends JpaRepository<NotaDeCorretagem, Integer> {
	
	List<NotaDeCorretagem> findAllByOrderByDateAsc();
}
