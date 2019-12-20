package cat.iot

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters

@SpringBootApplication
@EntityScan(basePackageClasses=[Iot2AuthApplication.class, Jsr310JpaConverters.class])
class Iot2AuthApplication {

	static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Bangkok"))
		Locale.setDefault(new Locale("th_TH"));
		
		SpringApplication.run Iot2AuthApplication, args
	}
}
