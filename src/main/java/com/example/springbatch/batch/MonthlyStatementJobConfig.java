package com.example.springbatch.batch;

import com.example.springbatch.domain.Account;
import com.example.springbatch.domain.Statement;
import com.example.springbatch.repository.AccountRepository;
import com.example.springbatch.repository.StatementRepository;
import com.example.springbatch.service.MonthlyStatementService;
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
public class MonthlyStatementJobConfig {

    @Bean
    public ItemReader<Account> statementAccountItemReader(AccountRepository accountRepository) {
        return new ListItemReader<>(accountRepository.findAll());
    }

    @Bean
    public ItemProcessor<Account, Statement> monthlyStatementProcessor(MonthlyStatementService monthlyStatementService) {
        return account -> monthlyStatementService.generateMonthlyStatement(account, java.time.LocalDate.now());
    }

    @Bean
    public ItemWriter<Statement> monthlyStatementWriter(StatementRepository statementRepository) {
        return statementRepository::saveAll;
    }

    @Bean
    public Step monthlyStatementStep(JobRepository jobRepository,
                                     PlatformTransactionManager transactionManager,
                                     ItemReader<Account> statementAccountItemReader,
                                     ItemProcessor<Account, Statement> monthlyStatementProcessor,
                                     ItemWriter<Statement> monthlyStatementWriter) {
        return new StepBuilder("monthlyStatementStep", jobRepository)
                .<Account, Statement>chunk(10, transactionManager)
                .reader(statementAccountItemReader)
                .processor(monthlyStatementProcessor)
                .writer(monthlyStatementWriter)
                .build();
    }

    @Bean
    public Job monthlyStatementJob(JobRepository jobRepository, Step monthlyStatementStep) {
        return new JobBuilder("monthlyStatementJob", jobRepository)
                .start(monthlyStatementStep)
                .build();
    }
}
