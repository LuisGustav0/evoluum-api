package com.evoluum.core.config.property;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties("app")
public class AppProperty {

    private final ServicoDadosIbgeGovBr servicoDadosIbgeGovBr = new ServicoDadosIbgeGovBr();

    @Getter
    public static class ServicoDadosIbgeGovBr {

        private final Localidades localidades = new Localidades();

        @Getter
        @Setter
        private String url;

        @Getter
        @Setter
        private String urn;

        public String getUriLocalidadesEstados() {
            String urnEstado = this.getLocalidades().getEstados().getUrn();

            return this.url.concat(this.urn).concat(urnEstado);
        }

        public String getUriLocalidadesEstadosMunicipios(String uf) {
            String urnMunicipio = this.getLocalidades().getEstados().getUrn();
            urnMunicipio = urnMunicipio.concat(this.getLocalidades()
                                                     .getEstados()
                                                     .getMunicipios()
                                                     .getUrn()
                                                     .replace("{UF}", uf));

            return this.url.concat(this.urn).concat(urnMunicipio);
        }

        @Getter
        public static class Localidades {

            private final Estados estados = new Estados();

            @Getter
            @Setter
            public static class Estados {
                private final Municipios municipios = new Municipios();

                private String urn;

                @Getter
                @Setter
                public static class Municipios {
                    private String urn;
                }
            }
        }
    }
}
