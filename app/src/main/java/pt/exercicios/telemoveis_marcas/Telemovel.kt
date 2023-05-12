package pt.exercicios.telemoveis_marcas

data class Telemovel(
    var nome: String,
    var descricao: String?,
    var ano: Long,
    var idMarca: String? = null,
    var id : Long = -1
){
}