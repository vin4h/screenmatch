package com.vin4h.screenmatch.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class Episodio {
    private Integer temporada;
    private String titulo;
    private Integer numeroEpisodio;
    private Double avaliacao;
    private LocalDate dataLacamento;

    public Episodio(Integer numeroTemporada, DadosEpisodio dadosEpisodio) {
        this.temporada = numeroTemporada;
        this.titulo = dadosEpisodio.titulo();
        this.numeroEpisodio = dadosEpisodio.numero();
        this.avaliacao = Optional.ofNullable(dadosEpisodio.avaliacao())
                .map(Double::valueOf)
                .orElse(0.0);
        try {
            this.dataLacamento = LocalDate.parse(dadosEpisodio.dataLancamento());
        } catch (DateTimeParseException e) {
            this.dataLacamento = null;
        }
    }

    public Integer getTemporada() {
        return temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public Integer getNumeroEpisodio() {
        return numeroEpisodio;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public LocalDate getDataLacamento() {
        return dataLacamento;
    }

    @Override
    public String toString() {
        return "temporada=" + temporada +
                ", titulo='" + titulo + '\'' +
                ", numeroEpisodio=" + numeroEpisodio +
                ", avaliacao=" + avaliacao +
                ", dataLacamento=" + dataLacamento;
    }
}
