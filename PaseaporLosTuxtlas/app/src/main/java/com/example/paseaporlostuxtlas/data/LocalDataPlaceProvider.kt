package com.example.paseaporlostuxtlas.data

import com.example.paseaporlostuxtlas.R
import com.example.paseaporlostuxtlas.model.Place
import com.example.paseaporlostuxtlas.model.PlaceType

object LocalDataPlaceProvider {
    val allFoodShops = listOf(
        Place(
            R.string.name_bicicleta,
            PlaceType.CAFETERIA,
            R.string.description_bicicleta,
            R.drawable.bicicleta
        ),
        Place(
            R.string.name_winnis,
            PlaceType.RESTAURANTE,
            R.string.description_winnis,
            R.drawable.winnis
        ),
        Place(
            R.string.name_chazaro,
            PlaceType.RESTAURANTE,
            R.string.description_chazaro,
            R.drawable.chazaro
        ),
        Place(
            R.string.name_antojitos,
            PlaceType.COMIDA_LOCAL,
            R.string.description_antojitos,
            R.drawable.antojitos
        ),
        Place(
            R.string.name_burguer_king,
            PlaceType.COMIDA_RAPIDA,
            R.string.description_burguer_king,
            R.drawable.burguer_king
        ),
        Place(
            R.string.name_angelotti,
            PlaceType.COMIDA_RAPIDA,
            R.string.description_angelotti,
            R.drawable.angelotti
        ),
        Place(
            R.string.name_tacos_tilapan,
            PlaceType.COMIDA_LOCAL,
            R.string.description_tacos_tilapan,
            R.drawable.tacos_tilapan
        )
    )

    val allFamiliarPlaces = listOf(
        Place(
            R.string.name_eyipantla,
            PlaceType.TURISTICO,
            R.string.description_eyipantla,
            R.drawable.eyipantla
        ),
        Place(
            R.string.name_parque_lerdo,
            PlaceType.PARQUE,
            R.string.description_parque_lerdo,
            R.drawable.parque_lerdo
        ),
        Place(
            R.string.name_museo_san_andres,
            PlaceType.TURISTICO,
            R.string.description_museo_san_andres,
            R.drawable.museo_san_andres
        ),
        Place(
            R.string.name_malecon,
            PlaceType.TURISTICO,
            R.string.description_malecon,
            R.drawable.malecon_catemaco
        ),
        Place(
            R.string.name_chichipilco,
            PlaceType.PARQUE,
            R.string.description_chichipilco,
            R.drawable.parque_chichipilco
        ),
        Place(
            R.string.name_catedral_catemaco,
            PlaceType.IGLESIA,
            R.string.description_catedral_catemaco,
            R.drawable.catedral_catemaco
        ),
        Place(
            R.string.name_nanciyaga,
            PlaceType.TURISTICO,
            R.string.description_nanciyaga,
            R.drawable.nanciyaga
        )
    )

    val allAcuaticsPlaces = listOf(
        Place(
            R.string.name_roca_partida,
            PlaceType.PLAYA,
            R.string.description_roca_partida,
            R.drawable.roca_partida
        ),
        Place(
            R.string.name_dos_abril,
            PlaceType.RIO,
            R.string.description_dos_abril,
            R.drawable.dos_abril
        ),
        Place(
            R.string.name_montepio,
            PlaceType.PLAYA,
            R.string.description_montepio,
            R.drawable.montepio
        ),
        Place(
            R.string.name_playa_hermosa,
            PlaceType.PLAYA,
            R.string.description_playa_hermosa,
            R.drawable.playa_hermosa
        ),
        Place(
            R.string.name_poza_enanos,
            PlaceType.POZA,
            R.string.description_poza_enanos,
            R.drawable.poza_enanos
        ),
        Place(
            R.string.name_alberca_morenos,
            PlaceType.ALBERCA,
            R.string.description_alberca_morenos,
            R.drawable.alberca_moreno
        ),
        Place(
            R.string.name_alberca_otapan,
            PlaceType.ALBERCA,
            R.string.description_alberca_otapan,
            R.drawable.alberca_otapan
        )
    )

    val allHotels = listOf(
        Place(
            R.string.name_piedra_alta,
            PlaceType.HOTEL,
            R.string.description_piedra_alta,
            R.drawable.piedra_alta
        ),
        Place(
            R.string.name_hotel_parque,
            PlaceType.HOTEL,
            R.string.description_hotel_parque,
            R.drawable.hotel_parque
        ),
        Place(
            R.string.name_hotel_michelle,
            PlaceType.HOTEL,
            R.string.description_hotel_michelle,
            R.drawable.hotel_michelle
        )
    )

    fun getAleatoryPlace() : Place {
        return (LocalDataPlaceProvider.allFamiliarPlaces +
                LocalDataPlaceProvider.allAcuaticsPlaces +
                LocalDataPlaceProvider.allFoodShops).random()
    }
}