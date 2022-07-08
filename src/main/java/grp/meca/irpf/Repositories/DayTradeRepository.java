package grp.meca.irpf.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import grp.meca.irpf.Models.DayTrade;

@Repository
public interface DayTradeRepository extends JpaRepository<DayTrade, Integer> {}
