package com.example.springbatch.batch;

import com.example.springbatch.domain.Account;
import com.example.springbatch.repository.AccountRepository;
import com.example.springbatch.service.InterestCalculationService;
import java.util.List;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class InterestJobConfig {

    @Bean
    public ItemReader<Account> accountItemReader(AccountRepository accountRepository) {
        return new ListItemReader<>(accountRepository.findAll());
    }

    @Bean
    public ItemProcessor<Account, Account> interestProcessor(InterestCalculationService interestCalculationService) {
        return account -> {
            if (account.getStatus() != null && account.getStatus().name().equals("ACTIVE")) {
                account.setBalance(account.getBalance().add(interestCalculationService.calculateDailyInterest(account, java.time.LocalDate.now())));
            }
            return account;
        };
    }

    @Bean
    public ItemWriter<Account> interestWriter(AccountRepository accountRepository) {
        return accountRepository::saveAll;
    }

    @Bean
    public Step dailyInterestStep(JobRepository jobRepository,
                                  PlatformTransactionManager transactionManager,
                                  ItemReader<Account> accountItemReader,
                                  ItemProcessor<Account, Account> interestProcessor,
                                  ItemWriter<Account> interestWriter) {
        return new StepBuilder("dailyInterestStep", jobRepository)
                .<Account, Account>chunk(10, transactionManager)
                .reader(accountItemReader)
                .processor(interestProcessor)
                .writer(interestWriter)
                .build();
    }

    @Bean
    public Job dailyInterestJob(JobRepository jobRepository, Step dailyInterestStep) {
        return new JobBuilder("dailyInterestJob", jobRepository)
                .start(dailyInterestStep)
                .build();
    }
}
