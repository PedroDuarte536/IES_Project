package yes.finance.services;

import yes.finance.model.Portfolio;
import yes.finance.repository.PortfolioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class PortfolioService {
    @Autowired
    private PortfolioRepository repository;

    public Portfolio savePortfolio(Portfolio Portfolio) {
        return repository.save(Portfolio);
    }

    public List<Portfolio> savePortfolios(List<Portfolio> Portfolios) {
        return repository.saveAll(Portfolios);
    }

    public List<Portfolio> getPortfolios() {
        return repository.findAll();
    }

    public Portfolio getPortfolioById(int id) {
        return repository.findById((int)id).orElse(null);
    }

    // IMPLEMENTAR TODOS OS FIND's QUE TIVERMOS NO PortfolioRepository.java

    public String deletePortfolio(int id) {
        repository.deleteById(id);
        return "Portfolio (id="+ id +") removed!";
    }

    public Portfolio updatePortfolio(Portfolio Portfolio) {
        Portfolio existingPortfolio = repository.findById((int)Portfolio.getId()).orElse(null);

        // FAZER SETS

        return savePortfolio(existingPortfolio);
    }
}