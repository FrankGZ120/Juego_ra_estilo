package mx.uacj.juego_ra.repositorios

import android.location.Location
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object repositorioUbicacion_singleton{
    @Provides
    @Singleton
    fun crear_repositorio_gestor_de_ubicacion(): RepositorioUbicacion{
        return InstanciaRepositorioUbicacion()
    }
}

class InstanciaRepositorioUbicacion(
    override val ubicacion: MutableState<Location> = mutableStateOf(Location("juego_ra"))
): RepositorioUbicacion {

}

interface RepositorioUbicacion{
    val ubicacion: MutableState<Location>
}