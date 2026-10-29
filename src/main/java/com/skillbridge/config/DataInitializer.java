package com.skillbridge.config;

import com.skillbridge.model.ChatMessage;
import com.skillbridge.model.SkillListing;
import com.skillbridge.model.SkillRequest;
import com.skillbridge.model.User;
import com.skillbridge.repository.ChatMessageRepository;
import com.skillbridge.repository.SkillListingRepository;
import com.skillbridge.repository.SkillRequestRepository;
import com.skillbridge.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final SkillListingRepository skillListingRepository;
    private final SkillRequestRepository skillRequestRepository;
    private final ChatMessageRepository chatMessageRepository;

    public DataInitializer(UserRepository userRepository,
                           SkillListingRepository skillListingRepository,
                           SkillRequestRepository skillRequestRepository,
                           ChatMessageRepository chatMessageRepository) {
        this.userRepository = userRepository;
        this.skillListingRepository = skillListingRepository;
        this.skillRequestRepository = skillRequestRepository;
        this.chatMessageRepository = chatMessageRepository;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            return; // Data already exists in database
        }

        // 1. Seed Student Profiles
        User alice = new User(
                "Alice Johnson",
                "alice@easwari.edu",
                "password123",
                "Easwari Engineering College",
                "Computer Science",
                "3rd Year",
                "Full-stack web developer passionate about React, Spring Boot, and cloud architecture. Looking forward to learning Machine Learning and conversational French!"
        );
        alice.setContactPhone("+91 98765 43210");
        alice.setAvatarUrl("https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=150");
        userRepository.save(alice);

        User bob = new User(
                "Bob Smith",
                "bob@easwari.edu",
                "password123",
                "Easwari Engineering College",
                "Data Science & AI",
                "4th Year",
                "AI enthusiast proficient in Python, PyTorch, and Data Analytics. Eager to master DevOps, Docker containerization, and modern frontend design."
        );
        bob.setContactPhone("+91 98765 11223");
        bob.setAvatarUrl("https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=150");
        userRepository.save(bob);

        User chloe = new User(
                "Chloe Davis",
                "chloe@easwari.edu",
                "password123",
                "Easwari Engineering College",
                "Information Technology",
                "2nd Year",
                "Graphic designer & Figma enthusiast. Excited to teach UI/UX prototyping and seeking peers to guide me through Data Structures & Algorithms in Java."
        );
        chloe.setContactPhone("+91 98765 99887");
        chloe.setAvatarUrl("https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=150");
        userRepository.save(chloe);

        User david = new User(
                "David Miller",
                "david@easwari.edu",
                "password123",
                "Easwari Engineering College",
                "Mechanical Engineering",
                "3rd Year",
                "Fluent German speaker and fingerstyle guitar player. Looking to learn basic web development and Python scripting for engineering simulations."
        );
        david.setContactPhone("+91 98765 55443");
        david.setAvatarUrl("https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=150");
        userRepository.save(david);

        // 2. Seed Skills: Both TEACH (offered skills) and LEARN (learning interests)
        // Alice
        SkillListing s1 = new SkillListing(alice, "Full-Stack Web Dev (React & Spring Boot)", "Programming", "TEACH", "EXPERT", "Learn how to build end-to-end full-stack applications with modern React hooks, REST APIs, and JPA.", "Weekends & Evenings");
        SkillListing s2 = new SkillListing(alice, "Figma UI/UX Prototyping", "Design", "TEACH", "ADVANCED", "Wireframing, interactive components, responsive design systems, and design handoff.", "Flexible");
        SkillListing s3 = new SkillListing(alice, "Machine Learning Foundations with Python", "Programming", "LEARN", "BEGINNER", "Seeking a peer mentor to guide me through Pandas, Scikit-learn, and regression/classification models.", "Tuesdays & Thursdays");
        SkillListing s4 = new SkillListing(alice, "Conversational French", "Languages", "LEARN", "BEGINNER", "Interested in practicing basic French greetings, everyday vocabulary, and simple grammar.", "Sunday mornings");

        // Bob
        SkillListing s5 = new SkillListing(bob, "Python for Data Science & ML", "Programming", "TEACH", "EXPERT", "Hands-on tutorials covering NumPy, Pandas, visualization, and building predictive models.", "Mon / Wed after 5 PM");
        SkillListing s6 = new SkillListing(bob, "Deep Learning with PyTorch", "Programming", "TEACH", "ADVANCED", "Neural networks, image classification with CNNs, and NLP fundamentals.", "Saturday afternoons");
        SkillListing s7 = new SkillListing(bob, "Docker & Cloud Deployment", "Programming", "LEARN", "INTERMEDIATE", "Wanting to learn production containerization, Docker Compose, and CI/CD basics.", "Weekends");

        // Chloe
        SkillListing s8 = new SkillListing(chloe, "Visual Design & Brand Identity", "Design", "TEACH", "ADVANCED", "Color theory, typography, creating logos, and designing sleek presentation decks.", "Flexible");
        SkillListing s9 = new SkillListing(chloe, "Data Structures & Algorithms in Java", "Programming", "LEARN", "BEGINNER", "Looking for peer explanation of binary trees, graph algorithms, and dynamic programming.", "Saturday mornings");

        // David
        SkillListing s10 = new SkillListing(david, "Conversational German (A1-B1)", "Languages", "TEACH", "ADVANCED", "Grammar basics, everyday conversation, pronunciation, and vocabulary building.", "Weekdays 6-8 PM");
        SkillListing s11 = new SkillListing(david, "Acoustic & Classical Guitar", "Music", "TEACH", "INTERMEDIATE", "Chords, fingerstyle, rhythm fundamentals, and playing your favorite songs.", "Sundays");
        SkillListing s12 = new SkillListing(david, "Intro to Web Design & HTML/CSS", "Programming", "LEARN", "BEGINNER", "Eager to learn how to design personal portfolio websites from scratch.", "Friday afternoons");

        skillListingRepository.save(s1);
        skillListingRepository.save(s2);
        skillListingRepository.save(s3);
        skillListingRepository.save(s4);
        skillListingRepository.save(s5);
        skillListingRepository.save(s6);
        skillListingRepository.save(s7);
        skillListingRepository.save(s8);
        skillListingRepository.save(s9);
        skillListingRepository.save(s10);
        skillListingRepository.save(s11);
        skillListingRepository.save(s12);

        // 3. Seed Exchange Proposal
        SkillRequest req1 = new SkillRequest(
                alice,
                bob,
                s5,
                "Full-Stack Web Dev (React & Spring)",
                "Hi Bob! I saw your Python & ML listing. I'd love to exchange lessons: I can teach you React & Spring Boot in return for Python ML foundations!"
        );
        req1.setStatus("ACCEPTED");
        skillRequestRepository.save(req1);

        // 4. Seed Messages in Integrated Chat
        ChatMessage m1 = new ChatMessage(alice, bob, "Hi Bob! Thanks for accepting the exchange proposal! When would you like to schedule our first session?", req1.getId());
        ChatMessage m2 = new ChatMessage(bob, alice, "Hey Alice! Excited to collaborate! How about Saturday at 4 PM in the library or via Google Meet?", req1.getId());
        chatMessageRepository.save(m1);
        chatMessageRepository.save(m2);

        System.out.println(">>> [SkillBridge] Seed data initialized successfully!");
    }
}
