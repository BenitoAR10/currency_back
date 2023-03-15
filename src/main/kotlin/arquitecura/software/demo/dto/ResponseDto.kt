package arquitecura.software.demo.dto

data class ResponseDto<T> @JvmOverloads constructor(
        var data: T? = null,
        var state: Boolean = false,
        var message: String? = null
)