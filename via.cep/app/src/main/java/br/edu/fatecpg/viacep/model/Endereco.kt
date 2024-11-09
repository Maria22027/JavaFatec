package br.edu.fatecpg.viacep.model

data class Endereco(
    val cep: String,
    val logradouro: String,
    val complemento: String,
    val bairro: String,
    val localidade: String,
    val uf: String,
    val ibge: String,
    val gia: String,
    val ddd: String,
    val siafi: String
) {
    override fun toString(): String {
        return "Endereço: $logradouro," +
                " $bairro, " +
                "$localidade - " +
                "$uf, " +
                "CEP: $cep"
    }
}
