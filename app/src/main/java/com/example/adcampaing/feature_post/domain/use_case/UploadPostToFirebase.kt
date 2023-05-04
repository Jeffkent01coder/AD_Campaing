import android.net.Uri
import com.example.adcampaing.feature_post.domain.repository.PostRepository
import javax.inject.Inject

class UploadPostUseCase @Inject constructor(
    private val repository: PostRepository
) {
    suspend operator fun invoke(uri: Uri) =
        repository.uploadPictureToFirebase(uri)
}