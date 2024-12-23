import android.content.Context
import androidx.room.Room
import com.example.marvel.data.HeroesRepository
import com.example.marvel.data.HeroesRepositoryImpl
import com.example.marvel.api.MarvelApiService
import com.example.marvel.data.database.AppDatabase
import com.example.marvel.data.database.SuperheroDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://gateway.marvel.com/v1/public/") // Marvel API base URL
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    fun provideMarvelApi(retrofit: Retrofit): MarvelApiService {
        return retrofit.create(MarvelApiService::class.java) // Use MarvelApiService explicitly
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "db")
            .build()
    }

    @Provides
    fun provideSuperheroDao(database: AppDatabase): SuperheroDao {
        return database.getSuperheroesDao()
    }

    @Singleton
    @Provides
    fun provideHeroesRepository(
        api: MarvelApiService,
        dao: SuperheroDao
    ): HeroesRepository {
        return HeroesRepositoryImpl(api, dao)
    }
}