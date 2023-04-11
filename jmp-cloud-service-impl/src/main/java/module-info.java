import com.epam.jmp.cloud.service.impl.ServiceImpl;
import com.epam.jmp.service.api.Service;

module jmp.cloud.service.impl {

    requires transitive jmp.service.api;

    exports com.epam.jmp.cloud.service.impl;

    provides Service with ServiceImpl;
}
