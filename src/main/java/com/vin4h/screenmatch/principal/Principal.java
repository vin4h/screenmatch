package com.vin4h.screenmatch.principal;

import com.vin4h.screenmatch.model.DadosSerie;
import com.vin4h.screenmatch.model.DadosTemporada;
import com.vin4h.screenmatch.service.ConsumoApi;
import com.vin4h.screenmatch.service.ConverteDados;

import java.util.*;

public class Principal {
    private final String BASE_URL = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";
    private final ConverteDados conversor = new ConverteDados();

    public void showMenu() {
        Scanner leitura = new Scanner(System.in);

        ConsumoApi consumo = new ConsumoApi();

        System.out.println("Digite o nome da serie para busca:");

        var nomeSerie = leitura.nextLine().replace(" ", "+");

        var json = consumo.obterDados(BASE_URL + nomeSerie + API_KEY);

        var dadosConvertidoSerie = conversor.obterDados(json, DadosSerie.class);

        List<DadosTemporada> listaDadosConvertidoTemporada = new ArrayList<>();

        for (int i = 1; i <= dadosConvertidoSerie.totalTemporadas(); i++) {
            var jsonTemporada = consumo.obterDados(BASE_URL + nomeSerie + "&season=" + i + API_KEY);
            listaDadosConvertidoTemporada.add(conversor.obterDados(jsonTemporada, DadosTemporada.class));
        }


//        listaDadosConvertidoTemporada.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        Map<Integer, Double> melhoresEpisodiosPorTemporada = new TreeMap<>();

        listaDadosConvertidoTemporada.forEach(dadosTemporada -> {
            dadosTemporada.episodios().stream()
                    .filter(episodio -> !episodio.avaliacao().equalsIgnoreCase("N/A"))
                    .mapToDouble(episodio -> Double.parseDouble(episodio.avaliacao()))
                    .max()
                    .ifPresent(melhorNota ->
                            melhoresEpisodiosPorTemporada.put(dadosTemporada.numero(), melhorNota)
                    );
        });

        System.out.println(melhoresEpisodiosPorTemporada);

    }
}