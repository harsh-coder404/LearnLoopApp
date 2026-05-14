package com.example.learnloop.data.models

object DummyData {

    val currentUser = User(
        id = "u1",
        name = "Alex Johnson",
        email = "alex@example.com",
        institutionEmail = "alex.johnson@university.edu",
        isVerified = true,
        knowledgeCredits = 47,
        reputationScore = 4.8f,
        teachingStreak = 5,
        totalSessionsTaught = 23,
        totalSessionsLearned = 12,
        profilePicture = null,
        bio = "CS student passionate about algorithms & ML. Love helping others debug!",
        languagesSpoken = listOf("English", "Hindi"),
        subjectExpertise = listOf(
            SubjectExpertise("se1", "Computer Science", "Expert", "u1"),
            SubjectExpertise("se2", "Mathematics", "Intermediate", "u1"),
            SubjectExpertise("se3", "Physics", "Beginner", "u1")
        )
    )

    val users = listOf(
        currentUser,
        User("u2", "Priya Sharma", "priya@example.com", "priya@iit.edu", true, 82, 4.9f, 12, 45, 8, null, "Math tutor extraordinaire", listOf("English", "Hindi"),
            listOf(SubjectExpertise("se4", "Mathematics", "Expert", "u2"), SubjectExpertise("se5", "Physics", "Expert", "u2"))),
        User("u3", "Carlos Rivera", "carlos@example.com", "carlos@mit.edu", true, 61, 4.7f, 7, 31, 15, null, "Physics & Engineering enthusiast", listOf("English", "Spanish"),
            listOf(SubjectExpertise("se6", "Physics", "Expert", "u3"), SubjectExpertise("se7", "Engineering", "Intermediate", "u3"))),
        User("u4", "Emily Chen", "emily@example.com", "emily@stanford.edu", true, 54, 4.6f, 3, 18, 22, null, "Biology and chemistry lover", listOf("English", "Mandarin"),
            listOf(SubjectExpertise("se8", "Biology", "Expert", "u4"), SubjectExpertise("se9", "Chemistry", "Intermediate", "u4"))),
        User("u5", "Rahul Gupta", "rahul@example.com", "rahul@bits.edu", true, 39, 4.5f, 0, 12, 30, null, "Economics & Finance student", listOf("English", "Hindi"),
            listOf(SubjectExpertise("se10", "Economics", "Intermediate", "u5"))),
        User("u6", "Sofia Rossi", "sofia@example.com", "sofia@unibo.edu", true, 95, 4.95f, 21, 67, 5, null, "Top CS tutor this semester", listOf("English", "Italian"),
            listOf(SubjectExpertise("se11", "Computer Science", "Expert", "u6"), SubjectExpertise("se12", "Mathematics", "Expert", "u6"))),
        User("u7", "James Kim", "james@example.com", "james@kaist.edu", true, 78, 4.85f, 9, 52, 10, null, null, listOf("English", "Korean"),
            listOf(SubjectExpertise("se13", "Physics", "Expert", "u7"))),
        User("u8", "Aisha Patel", "aisha@example.com", "aisha@iim.edu", true, 43, 4.4f, 4, 20, 18, null, null, listOf("English", "Hindi", "Gujarati"),
            listOf(SubjectExpertise("se14", "Economics", "Expert", "u8"), SubjectExpertise("se15", "Mathematics", "Intermediate", "u8")))
    )

    val helpRequests = listOf(
        HelpRequest("r1", "Computer Science", "Binary Search Tree deletion", "I'm confused about how deletion works in BST, especially with two children nodes. Can someone walk me through with examples?", "English", "HIGH", 45, 3, "OPEN", "2 min ago", users[4]),
        HelpRequest("r2", "Mathematics", "Integration by parts", "Need help understanding when to use integration by parts vs substitution. Please explain with multiple examples.", "English", "MEDIUM", 30, 2, "OPEN", "15 min ago", users[3]),
        HelpRequest("r3", "Physics", "Quantum entanglement basics", "Can someone explain quantum entanglement in simple terms? I understand classical mechanics but QM is confusing me.", "English", "LOW", 60, 2, "OPEN", "1 hr ago", users[7]),
        HelpRequest("r4", "Computer Science", "React hooks and state management", "Getting confused with useEffect dependencies and state updates. Need a live debugging session.", "English", "URGENT", 30, 3, "OPEN", "5 min ago", users[2]),
        HelpRequest("r5", "Biology", "CRISPR-Cas9 mechanism", "Preparing for an exam tomorrow. Need a quick walkthrough of CRISPR-Cas9 editing mechanism.", "English", "URGENT", 45, 5, "OPEN", "8 min ago", users[1]),
        HelpRequest("r6", "Economics", "Game theory Nash equilibrium", "Struggling with Nash equilibrium problems in my microeconomics course.", "English", "MEDIUM", 30, 2, "MATCHED", "2 hr ago", users[4], "u1"),
        HelpRequest("r7", "Mathematics", "Linear algebra eigenvalues", "Need help computing eigenvalues and understanding their geometric meaning.", "English", "LOW", 45, 2, "OPEN", "3 hr ago", users[2])
    )

    val activeSessions = listOf(
        Session("s1", "10:30 AM", null, "ACTIVE", null, emptyList(), 3,
            helpRequests[5], users[0], users[4]),
        Session("s2", "Tomorrow 2:00 PM", null, "SCHEDULED", null, emptyList(), 2,
            helpRequests[1], users[1], users[3])
    )

    val completedSessions = listOf(
        Session("s3", "Yesterday 4:00 PM", "Yesterday 4:45 PM", "COMPLETED", "Covered BST operations thoroughly", emptyList(), 3,
            helpRequests[0], users[0], users[4]),
        Session("s4", "2 days ago", "2 days ago", "COMPLETED", "Explained integration techniques", emptyList(), 2,
            helpRequests[1], users[0], users[3]),
        Session("s5", "3 days ago", "3 days ago", "COMPLETED", "Quantum basics covered", emptyList(), 2,
            helpRequests[2], users[2], users[0])
    )

    val allSessions = activeSessions + completedSessions

    val creditTransactions = listOf(
        CreditTransaction("t1", "EARNED", 3, "Session: BST deletion help", "Today, 4:45 PM", "s3"),
        CreditTransaction("t2", "EARNED", 2, "Session: Integration techniques", "Yesterday, 3:30 PM", "s4"),
        CreditTransaction("t3", "BONUS", 5, "Welcome bonus — account verified!", "2 days ago", null),
        CreditTransaction("t4", "SPENT", 2, "Session: Quantum entanglement basics", "3 days ago", "s5"),
        CreditTransaction("t5", "EARNED", 3, "Session: React hooks debugging", "4 days ago", "s6"),
        CreditTransaction("t6", "EARNED", 2, "Session: Calculus review", "5 days ago", "s7"),
        CreditTransaction("t7", "SPENT", 3, "Session: Machine learning basics", "6 days ago", "s8"),
        CreditTransaction("t8", "BONUS", 20, "Account created — starter credits", "7 days ago", null),
        CreditTransaction("t9", "EARNED", 4, "Session: Data structures deep dive", "8 days ago", "s9"),
        CreditTransaction("t10", "SPENT", 1, "Session: Economics overview", "9 days ago", "s10")
    )

    val notifications = listOf(
        Notification("n1", "Priya Sharma accepted your Math session request!", "MATCH_FOUND", false, "2 min ago"),
        Notification("n2", "Your session with Carlos Rivera starts in 10 minutes.", "SESSION_STARTING", false, "10 min ago"),
        Notification("n3", "You earned 3 credits from the BST session!", "CREDITS_EARNED", false, "1 hr ago"),
        Notification("n4", "🏆 You unlocked the 'Knowledge Sharer' badge!", "BADGE_UNLOCKED", true, "Yesterday"),
        Notification("n5", "Emily Chen posted a Biology request matching your expertise.", "MATCH_FOUND", true, "Yesterday"),
        Notification("n6", "You earned 2 credits from the Integration session.", "CREDITS_EARNED", true, "2 days ago"),
        Notification("n7", "New URGENT request in Computer Science!", "MATCH_FOUND", true, "2 days ago")
    )

    val badges = listOf(
        Badge("b1", "First Teach", "Completed your first session as a tutor", "🎓", true, "Earned!"),
        Badge("b2", "Knowledge Sharer", "Taught 5 sessions successfully", "📚", true, "Earned!"),
        Badge("b3", "Subject Expert: CS", "Teach 10 sessions in Computer Science", "⭐", false, "7/10 sessions"),
        Badge("b4", "Top Contributor", "Reach the top 10 on the leaderboard", "🏆", false, "Currently #14"),
        Badge("b5", "Speed Helper", "Accept and start an URGENT request within 5 minutes", "⚡", false, "Not yet earned")
    )

    val leaderboard = listOf(
        LeaderboardEntry(1, users[5], 95, 67),
        LeaderboardEntry(2, users[1], 82, 45),
        LeaderboardEntry(3, users[6], 78, 52),
        LeaderboardEntry(4, users[2], 61, 31),
        LeaderboardEntry(5, users[0], 47, 23),
        LeaderboardEntry(6, users[3], 54, 18),
        LeaderboardEntry(7, users[7], 43, 20),
        LeaderboardEntry(8, users[4], 39, 12),
        LeaderboardEntry(9, User("u9", "Marco Bianchi", "", "", true, 35, 4.3f, 2, 9, 6), 35, 9),
        LeaderboardEntry(10, User("u10", "Yuki Tanaka", "", "", true, 28, 4.2f, 0, 7, 14), 28, 7)
    )

    val chatMessages = listOf(
        ChatMessage("m1", "u4", "Rahul Gupta", "Hi! I'm really struggling with BST deletion. Especially when the node has two children.", "TEXT", "10:31 AM"),
        ChatMessage("m2", "u1", "Alex Johnson", "Sure, I'll walk you through it! The key is finding the in-order successor (smallest node in right subtree).", "TEXT", "10:32 AM"),
        ChatMessage("m3", "u1", "Alex Johnson", "Here's the pseudocode:\n\nfunction delete(node, key):\n  if key < node.val: node.left = delete(node.left, key)\n  elif key > node.val: node.right = delete(node.right, key)\n  else:\n    if node.left is None: return node.right\n    if node.right is None: return node.left\n    successor = findMin(node.right)\n    node.val = successor.val\n    node.right = delete(node.right, successor.val)\n  return node", "CODE", "10:33 AM"),
        ChatMessage("m4", "u4", "Rahul Gupta", "Oh that makes sense! So we replace the node's value with the successor and then delete the successor?", "TEXT", "10:35 AM"),
        ChatMessage("m5", "u1", "Alex Johnson", "Exactly! And the in-order successor always has at most one right child, so its deletion is straightforward.", "TEXT", "10:36 AM"),
        ChatMessage("m6", "u4", "Rahul Gupta", "Got it! What about the time complexity?", "TEXT", "10:37 AM"),
        ChatMessage("m7", "u1", "Alex Johnson", "O(h) where h is tree height. For balanced BST that's O(log n), worst case O(n) for skewed trees.", "TEXT", "10:38 AM")
    )

    val subjects = listOf(
        "Mathematics", "Physics", "Chemistry", "Computer Science",
        "Biology", "Economics", "History", "Literature",
        "Engineering", "Psychology", "Statistics", "English"
    )

    val languages = listOf(
        "English", "Hindi", "Spanish", "French", "German",
        "Mandarin", "Arabic", "Portuguese", "Italian", "Korean",
        "Japanese", "Russian", "Gujarati", "Tamil", "Telugu"
    )
}
