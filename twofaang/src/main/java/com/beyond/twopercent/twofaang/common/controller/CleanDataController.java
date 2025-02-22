package com.beyond.twopercent.twofaang.common.controller;


import com.beyond.twopercent.twofaang.common.service.CleanDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@EnableScheduling
@RequestMapping("/clean")
@RequiredArgsConstructor
@Tag(name = "DB 최적화 APIs", description = "DB 최적화 API 리스트")
public class CleanDataController {

    private final CleanDataService cleanDataService;

    @DeleteMapping("/refresh")
    @Scheduled(cron = "0 0/30 * * * *")
    @Operation(summary = "Refresh 토큰 데이터 최적화", description = "만료된 Refresh 토큰 데이터들을 삭제한다.")
    public void CleanRefreshToken() {
        try {
            cleanDataService.cleanRefreshToken();
        } catch (Exception e) {
            log.error("Unexpected error occurred in scheduled task", e);
        }
    }

    @DeleteMapping("/auth-code")
    @Scheduled(cron = "0 0/30 * * * *")
    @Operation(summary = "인증 코드 데이터 최적화", description = "만료된 인증 코드 데이터들을 삭제한다.")
    public void CleanAuthCode() {
        try {
            cleanDataService.cleanAuthCode();
        } catch (Exception e) {
            log.error("Unexpected error occurred in scheduled task", e);
        }
    }
}
