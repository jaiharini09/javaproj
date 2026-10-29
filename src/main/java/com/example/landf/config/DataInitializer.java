package com.example.landf.config;

import com.example.landf.model.ChatMessage;
import com.example.landf.model.SkillListing;
import com.example.landf.model.SkillRequest;
import com.example.landf.model.User;
import com.example.landf.repository.ChatMessageRepository;
import com.example.landf.repository.SkillListingRepository;
import com.example.landf.repository.SkillRequestRepository;
import com.example.landf.repository.UserRepository;
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
            return; // Data already exists
        }

        // 1. Seed Students
        User alice = new User(
                "Alice Johnson",
                "alice@easwari.edu",
                "password123",
                "Easwari Engineering College",
                "Computer Science",
                "3rd Year",
                "Passionate about Web Development (React & Spring Boot) and UI/UX design. Looking to learn Machine Learning and French!"
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
                "AI enthusiast proficient in Python, PyTorch, and Data Analytics. Eager to master Cloud Deployment and Docker!"
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
                "Graphic designer & Figma wizard. Wanting to learn Python data structures and prepare for technical interviews."
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
                "Classical guitar player and fluent German speaker. I'd love to learn basic programming in Java or Web design!"
        );
        david.setContactPhone("+91 98765 55443");
        david.setAvatarUrl("https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=150");
        userRepository.save(david);

        // 2. Seed Skills
        // Alice
        SkillListing s1 = new SkillListing(alice, "Full-Stack Web Development (React + Spring)", "Programming", "TEACH", "EXPERT", "I can teach modern React hooks, state management, and building RESTful APIs with Java Spring Boot.", "Weekends & Evenings");
        SkillListing s2 = new SkillListing(alice, "Figma UI/UX Prototyping", "Design", "TEACH", "ADVANCED", "Wireframing, interactive mockups, design systems, and developer handoffs.", "Flexible");
        SkillListing s3 = new SkillListing(alice, "Machine Learning Foundations with Python", "Programming", "LEARN", "BEGINNER", "Seeking a peer mentor to guide me through scikit-learn and model evaluation.", "Tuesdays & Thursdays");

        // Bob
        SkillListing s4 = new SkillListing(bob, "Python for Data Science & ML", "Programming", "TEACH", "EXPERT", "Hands-on tutorials in Pandas, NumPy, scikit-learn, and neural network basics.", "Mon / Wed after 5 PM");
        SkillListing s5 = new SkillListing(bob, "Docker & Containerization", "Programming", "LEARN", "INTERMEDIATE", "Wanting to learn production containerization, Docker Compose, and CI/CD basics.", "Weekends");

        // Chloe
        SkillListing s6 = new SkillListing(chloe, "Visual Design & Brand Identity", "Design", "TEACH", "ADVANCED", "Color theory, typography, creating logos and sleek presentation decks.", "Flexible");
        SkillListing s7 = new SkillListing(chloe, "Data Structures & Algorithms in Java", "Programming", "LEARN", "BEGINNER", "Looking for someone to explain recursion, trees, and dynamic programming.", "Saturday mornings");

        // David
        SkillListing s8 = new SkillListing(david, "Conversational German (A1-B1)", "Languages", "TEACH", "ADVANCED", "Pronunciation, daily conversation, grammar basics, and vocabulary building.", "Weekdays 6-8 PM");
        SkillListing s9 = new SkillListing(david, "Acoustic & Classical Guitar", "Music", "TEACH", "INTERMEDIATE", "Chords, fingerstyle, rhythm fundamentals, and playing your favorite songs.", "Sundays");

        skillListingRepository.save(s1);
        skillListingRepository.save(s2);
        skillListingRepository.save(s3);
        skillListingRepository.save(s4);
        skillListingRepository.save(s5);
        skillListingRepository.save(s6);
        skillListingRepository.save(s7);
        skillListingRepository.save(s8);
        skillListingRepository.save(s9);

        // 3. Seed Exchange Request
        SkillRequest req1 = new SkillRequest(
                alice,
                bob,
                s4,
                "React & Modern Web Design",
                "Hi Bob! I saw your Python & ML listing. I'd love to exchange lessons: I can teach you React/Spring Boot web dev in return for ML basics!"
        );
        req1.setStatus("ACCEPTED");
        skillRequestRepository.save(req1);

        // 4. Seed Messages
        ChatMessage m1 = new ChatMessage(alice, bob, "Hi Bob! Thanks for accepting the exchange request! When are you free for our first session?", req1.getId());
        ChatMessage m2 = new ChatMessage(bob, alice, "Hey Alice! Glad to connect! How about Saturday at 4 PM at the campus library or Google Meet?", req1.getId());
        chatMessageRepository.save(m1);
        chatMessageRepository.save(m2);

        System.out.println(">>> SkillBridge Seed Data initialized successfully!");
    }
}
