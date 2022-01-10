package yes.finance.controller;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;

import yes.finance.model.*;
import yes.finance.model.Currency;
import yes.finance.services.*;

@CrossOrigin
@RestController
public class FinanceController {

    @Autowired
    private UserService service;
    @Autowired
    private CurrencyService currencyservice;
    @Autowired
    private ExtensionService extensionservice;
    @Autowired
    private MarketService marketservice;
    @Autowired
    private OrderService orderservice;
    @Autowired
    private TransactionService transactionservice;
    @Autowired
    private PortfolioService portfolioservice;
    @Autowired
    private TickerService tickerservice;

    //////////////////////////////////////////// USER
    //////////////////////////////////////////// ////////////////////////////////////////////

    @GetMapping("/user")
    public Page<User> getAllUsers(Pageable pageable) {
        return service.getUsers(pageable);
    }

    @PostMapping("/user")
    public User createUsers(@RequestBody User user) {
        return service.saveUser(user);
    }

    @DeleteMapping("/user/{id}")
    public String deleteUsers(@PathVariable int id) {
        return service.deleteUser(id);
    }

    //////////////////////////////////////////// CURRENCY
    //////////////////////////////////////////// ////////////////////////////////////////////

    // obter info sobre a moeda (nome, symbol e isso)
    @GetMapping("/currency/info/{id}")
    public Currency getCurrencyInfo(@PathVariable int id) {
        return currencyservice.getCurrencyById(id);
    }

    @GetMapping("/currency")
    public Page<Currency> getAllCurrencies(Pageable pageable) {
        return currencyservice.getCurrencies(pageable);
    }

    @PostMapping("/currency")
    public Currency createCurrencies(@RequestBody Currency currency) {
        return currencyservice.saveCurrency(currency);
    }

    @DeleteMapping("/currency/del/{id}")
    public String deleteCurrency(@PathVariable int id) {
        return currencyservice.deleteCurrency(id);
    }

    @GetMapping("/currency/{id}")
    public Page<Market> getMarketsByCurrencyId(@PathVariable(value = "id") int currencyId) {
        List<Market> markets_by_currency = marketservice.getMarketsByCurrency(currencyId);
        Page<Market> page_markets_by_currency = new PageImpl<>(markets_by_currency);
        return page_markets_by_currency;
    }

    //////////////////////////////////////////// EXTENSION
    //////////////////////////////////////////// ////////////////////////////////////////////

    @GetMapping("/extension")
    public Page<Extension> getAllExtensions(Pageable pageable) {
        return extensionservice.getExtensions(pageable);
    }

    @PostMapping("/extension")
    public Extension createExtensions(@RequestBody Extension extension) {
        return extensionservice.saveExtension(extension);
    }

    @DeleteMapping("/extension/{id}")
    public String deleteExtensions(@PathVariable int id) {
        return extensionservice.deleteExtension(id);
    }

    //////////////////////////////////////////// PORTFOLIO
    //////////////////////////////////////////// ////////////////////////////////////////////

    // receber todos os portfolios de um user ID (tem de ter um parametro id!)
    @GetMapping("/portfolio")
    public List<Portfolio> getAllPortfolios() {
        return portfolioservice.getPortfolios();
    }

    @DeleteMapping("/portfolio/{id}")
    public String deletePortfolios(@PathVariable int id) {
        return portfolioservice.deletePortfolio(id);
    }

    // recebe um post do angular com os parametros name e user (maybe)
    @PostMapping("/portfolio")
    public Portfolio createPortfolio(@RequestBody String name) {

        System.out.println(">> A criar Portfolio '" + name + "'...");

        return portfolioservice.savePortfolio(new Portfolio(name));
    }

    @GetMapping("/portfolio/{id}")
    public Portfolio getPortfolio(@PathVariable int id) {
        return portfolioservice.getPortfolioById(id);
    }

    //////////////////////////////////////////// MARKET
    //////////////////////////////////////////// ////////////////////////////////////////////

    // EndPoint para os gráficos
    @GetMapping("/market/info/{id}")
    public Market getMarket(@PathVariable(value = "id") int marketId) {
        return marketservice.getMarketById(marketId);
    }

    @PostMapping("/market")
    public Market createMarkets(@RequestBody Market market) {
        return marketservice.saveMarket(market);
    }

    @GetMapping("/market")
    public Page<Market> getAllMarkets(Pageable pageable) {
        return marketservice.getMarkets(pageable);
    }

    // EndPoint para os gráficos
    @GetMapping("/market/{id}")
    public List<Ticker> getTickersByMarketId(@PathVariable(value = "id") int marketId) {
        return tickerservice.getTickersbyMarketID(marketId);
    }

    /*
     * // retorna uma lista dos preços atuais de cada mercado
     * 
     * @GetMapping("/market2")
     * public List<Float> getPrice() {
     * // List<Market> markets = marketservice.getMarkets();
     * // List<Float> prices = new ArrayList<>();
     * 
     * // for (Market market : markets) {
     * // Ticker last_ticker = market.getTickers().get(0);
     * // prices.add( last_ticker.getPrev_value() );
     * // }
     * 
     * // return prices;
     * 
     * List<Float> prices = new ArrayList<>();
     * 
     * Map<String, Object> marketSerialized = new HashMap<>();
     * marketSerialized.put("id", market.getId());
     * marketSerialized.put("originCurrency", market.getOriginCurrency());
     * marketSerialized.put("destinyCurrency", market.getDestinyCurrency());
     * marketSerialized.put("tickers", tickers);
     * 
     * return marketSerialized;
     * }
     */

    @GetMapping("/market/{id}/orders/sell")
    public Page<Order> getMarketSellOrders(@PathVariable int id, Pageable pageable) {
        return orderservice.getSellOrdersByMarket(id, pageable);
    }

    @GetMapping("/market/{id}/orders/buy")
    public Page<Order> getMarketBuyOrders(@PathVariable int id, Pageable pageable) {
        return orderservice.getBuyOrdersByMarket(id, pageable);
    }

    //////////////////////////////////////////// ORDER
    //////////////////////////////////////////// ////////////////////////////////////////////

    @GetMapping("/order")
    public Page<Order> getAllOrders(Pageable pageable) {
        return orderservice.getOrders(pageable);
    }

    @PostMapping("/order")
    public Order createOrders(@RequestBody Order order) {
        return orderservice.saveOrder(order);
    }

    @DeleteMapping("/order/{id}")
    public String deleteOrder(@PathVariable int id) {
        return orderservice.deleteOrder(id);
    }

    //////////////////////////////////////////// TRANSACTION
    //////////////////////////////////////////// ////////////////////////////////////////////

    @GetMapping("/transaction")
    public Page<Transaction> getAllTransactions(Pageable pageable) {
        return transactionservice.getTransactions(pageable);
    }

    @PostMapping("/transaction")
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return transactionservice.saveTransaction(transaction);
    }

    @DeleteMapping("/transaction/{id}")
    public String deleteTransaction(@PathVariable int id) {
        return transactionservice.deleteTransaction(id);
    }

    //////////////////////////////////////////// TICKER
    //////////////////////////////////////////// ////////////////////////////////////////////

    @GetMapping("/ticker")
    public Page<Ticker> getAllTickers(Pageable pageable) {
        return tickerservice.getTickers(pageable);
    }

    @PostMapping("/ticker")
    public Ticker createTicker(@RequestBody Ticker ticker) {
        return tickerservice.saveTicker(ticker);
    }

    @DeleteMapping("/ticker/{id}")
    public String deleteTicker(@PathVariable int id) {
        return tickerservice.deleteTicker(id);
    }

}