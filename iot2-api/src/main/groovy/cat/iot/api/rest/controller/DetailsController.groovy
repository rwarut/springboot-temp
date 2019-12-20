package cat.iot.api.rest.controller

import cat.iot.api.service.MasterDataService
import cat.iot.db.domain.MasterCusType
import cat.iot.db.domain.MasterCustomerGroup
import cat.iot.db.domain.MasterGender
import cat.iot.db.domain.MasterPostcode
import org.springframework.web.bind.annotation.RequestBody

import java.security.Principal

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

import cat.iot.db.domain.MasterDeviceProfile

@Controller
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
class DetailsController {

	@Autowired
	MasterDataService masterDataService
	
	@RequestMapping(value = "/core/deviceProfiles", method=RequestMethod.GET)
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_USER')")
	List<MasterDeviceProfile> getDeviceProfiles(Principal p) {
		return masterDataService.getDeviceProfiles()
	}

}
